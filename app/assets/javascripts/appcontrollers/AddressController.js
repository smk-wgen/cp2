/**
 * Created by skunnumkal on 7/15/13.
 */
function AddressController($scope,addressService,userService){
    'use strict';
    $scope.addresses = []; //addressService.getUserAddresses(userService.currentUser.id);
    $scope.line1 = "Street name";
    $scope.label = "Label of Address";
    $scope.line2 = '';
    $scope.city = "Chewburg";
    $scope.state = "PA";
    $scope.zip = 10944;
    $scope.isAddressModalOpen = false;
    $scope.userService = userService;
    $scope.currentUser = {};

    console.log("in AddressController..Current User = " + userService.currentUser);
    $scope.$watch('userService.currentUser',function(newValue, oldValue){
           if(newValue !== undefined || newValue != null){
                 console.log("Value of currentUser just changed");
                 console.log("New Value ",newValue);
                 $scope.currentUser = newValue;
           }
    });

    $scope.addAddress = function(){
        console.log("Adding an address for user",$scope.currentUser.id);
        var addressPostJson = {
          id : '',
          label : $scope.label,
          line1 : $scope.line1,
          line2 : $scope.line2,
          city : $scope.city,
          zip : $scope.zip,
          state : $scope.state,
          userId : $scope.currentUser.id
        };
        var promise = addressService.addUserAddress(addressPostJson);
        promise.then(function(response){
             $scope.isAddressModalOpen = false;
             $scope.addresses.push(response.data);
        },function(errResponse){
            alert("Something went wrong");
            console.error(errResponse.data);
        });

    };

    $scope.getAddresses = function(){
         var addressListPromise = addressService.getUserAddresses(userService.currentUser.id);
        addressListPromise.then(function(response){
            console.log("Got the addresses");
            $scope.addresses = response.data;
        });
    };

    $scope.openAddressModal = function(){
        $scope.isAddressModalOpen = true;
    };

    $scope.closeAddressModal = function(){
       $scope.isAddressModalOpen = false;
    };
    $scope.opts = {
        backdropFade: true,
        dialogFade:true
    };

    //$scope.getAddresses();

}
AddressController.$inject = ['$scope','addressService','userService'];