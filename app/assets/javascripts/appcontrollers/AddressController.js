/**
 * Created by skunnumkal on 7/15/13.
 */
function AddressController($scope,addressService,userService){
    'use strict';
    $scope.addresses = [];
    $scope.label = "Address" + $scope.addresses.length;
    $scope.line1 = "130 Madison Ave";

    $scope.line2 = '2nd Floor';
    $scope.city = "New York";
    $scope.state = "NY";
    $scope.zip = 10016;
    $scope.isAddressModalOpen = false;
    $scope.userService = userService;
    $scope.currentUser = undefined;

    var getAddresses = function(){

        var addressListPromise = addressService.getUserAddresses($scope.currentUser.id);
        addressListPromise.then(function(response){

            $scope.addresses = response;


        });
    };

    $scope.$watch('userService.currentUser.id',function(newValue, oldValue){
           if(newValue !== undefined || newValue != null){

                 console.log("Address Controller, New Value of current User Id",newValue);
                 $scope.currentUser = newValue;
                 if($scope.currentUser.id !== undefined){
                     getAddresses();

                 }

           }
    });

    $scope.addAddress = function(){
        var user = userService.currentUser;
        console.log("Adding an address for user",user.id);
        var addressPostJson = {
          id : '',
          label : $scope.label,
          line1 : $scope.line1,
          line2 : $scope.line2,
          city : $scope.city,
          zip : $scope.zip,
          state : $scope.state,
          userId : user.id
        };
        var promise = addressService.addUserAddress(addressPostJson);
        promise.then(function(response){
             $scope.isAddressModalOpen = false;
             $scope.addresses.push(response);
             $scope.label = 'Address'+$scope.addresses.length;
        },function(errResponse){
            alert("Something went wrong");
            console.error(errResponse.data);
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