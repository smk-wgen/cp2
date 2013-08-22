/**
 * Created by skunnumkal on 7/15/13.
 */
function CommuteTimeController($scope,addressService,$http,userService,commuteService,$location){
    'use strict';
    $scope.startTime = 420;
    $scope.endTime = 570;
    $scope.isCommuteModalOpen = false;


    $scope.ucas = addressService.myAddresses;

    $scope.startaddress = '';
    $scope.endaddress = '';


    $scope.isNotSameAsStart = function(address){
        return $scope.startaddress.label !== address.label;
    };
    $scope.submitCommute = function(){
        $scope.startTimeAsInt = $("#slider-range").slider("values", 0);
        $scope.endTimeAsInt = $("#slider-range").slider("values", 1);
        console.log($scope.startTime,$scope.endTime,$scope.startaddress,$scope.startTimeAsInt,$scope.endTimeAsInt);
        $http.post("/usercommute", { id: '',startAddress: $scope.startaddress.id, endaddress: $scope.endaddress.id,
            startTime: $scope.startTimeAsInt, endTime: $scope.endTimeAsInt , userId : userService.currentUser.id })
            .success(function(data){
                console.log(data);
                //make rest of the page visible

            });
    };

    $scope.getUserCommutes = function(){
        var commuteListPromise = commuteService.getUserCommutes(userService.currentUser.id);
        commuteListPromise.then(function(response){

            $scope.commutes = response.data;

            console.log("Got the user's commutes");


        });
    };
    //$scope.getUserCommutes();

    $scope.openCommuteModal = function(){
       $scope.isCommuteModalOpen = true;
    };

    $scope.closeCommuteModal = function(){
       $scope.isCommuteModalOpen = false;
    };

    $scope.opts = {
        backdropFade: true,
        dialogFade:true
    };


}
CommuteTimeController.$inject = ['$scope','addressService','$http','userService','commuteService','$location'];