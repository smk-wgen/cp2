/**
 * Created by skunnumkal on 7/15/13.
 */
function AddressController($scope,$http,addressService,userService){
    'use strict';
    $scope.$watch('addressService.myAddresses',function(newValue,oldValue){
        console.log('Addresses changed');
    });
    $scope.addresses = addressService.getUserAddresses(userService.currentUser);
    $scope.userId = userService.currentUser; //get this from a service
    $scope.line1 = "Street name";
    $scope.label = "Label of Address";
    $scope.line2 = '';
    $scope.city = "Chewburg";
    $scope.state = "PA";
    $scope.zip = 10944;
    $scope.addAddress = function(){
        $http.post("/address", { id : '', label: $scope.label, line1: $scope.line1,
            line2: $scope.line2, city: $scope.city , zip : $scope.zip , state : $scope.state,
            userId : $scope.userId})
            .success(function(data){
                console.log(data);
                //$scope.addresses.push({name:$scope.label});
                addressService.addAddress({
                    label : $scope.label, line1: $scope.line1,
                    line2: $scope.line2, city: $scope.city , zip : $scope.zip , state : $scope.state,
                    userId : $scope.userId
                });
                //make rest of the page visible

            });
    };

}
AddressController.$inject = ['$scope','$http','addressService','userService'];