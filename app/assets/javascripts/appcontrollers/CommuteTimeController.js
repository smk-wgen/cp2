/**
 * Created by skunnumkal on 7/15/13.
 */
function CommuteTimeController($scope,addressService,$http,userService,commuteService){
    'use strict';
    $scope.startTime = 420;
    $scope.endTime = 570;
    $scope.isCommuteModalOpen = false;
    $scope.userService = userService;
    $scope.commutes = [];
    $scope.currentUser = '';
    $scope.commuteStartTimes = [{'label' :'Before 6 am'},{'label':'6 am - 7 am'},{'label':'7 am - 8 am'},{'label':'8 am - 9 am'},
        {'label':'After 9 am'}]; //];
    $scope.timeInterval = '';
    $scope.$watch('userService.currentUser',function(newValue,oldValue){
           $scope.currentUser = newValue;
        if($scope.currentUser.id !== undefined){
            addressService.getUserAddresses($scope.currentUser.id).then(function(response){
                $scope.ucas = response.data;
                console.log("Ucas",$scope.ucas);
                getUserCommutes();
            });


        }
    });



    $scope.startaddress = '';
    $scope.endaddress = '';

    var getUserCommutes = function(){
        var commuteListPromise = commuteService.getUserCommutes($scope.currentUser.id);
        commuteListPromise.then(function(response){

            $scope.commutes = response.data;


        });
    };

    var getTimeInterval = function(){
       var interval = {start : 0, end : 0};
       angular.forEach($scope.commuteStartTimes, function(commuteStartTime,index){
           if(commuteStartTime.label === $scope.timeInterval.label){
               if(index === 0 ){
                   interval.start = 240; interval.end = 360;
               }
               else if(index === 1){
                   interval.start = 360; interval.end = 420;
               }
               else if(index === 2){
                   interval.start = 420; interval.end = 480;
               } else if(index === 3){
                   interval.start = 480; interval.end = 540;
               } else if(index === 4)
               { interval.start = 540 ; interva.end =  660};
           }
       });
       return interval;
    };
    $scope.isNotSameAsStart = function(address){
        return $scope.startaddress.label !== address.label;
    };
    $scope.submitCommute = function(){
        var timeInterval = getTimeInterval();
        $scope.startTimeAsInt = timeInterval.start;
        $scope.endTimeAsInt = timeInterval.end;
        console.log("Stats",$scope.startTime,$scope.endTime,$scope.startaddress,$scope.startTimeAsInt,$scope.endTimeAsInt);
        var commute = {
         id: '',
         startAddress: $scope.startaddress.id,
         endAddress: $scope.endaddress.id,
         startTime: $scope.startTimeAsInt,
         endTime: $scope.endTimeAsInt ,
         user : $scope.currentUser.id
        };
        console.log(commute);
        var promisedCommute = commuteService.addUserCommute(commute);

        promisedCommute.then(function(response){
                console.log(response.data);
                //make rest of the page visible
                $scope.isCommuteModalOpen = false;
                $scope.commutes.push(response.data);
            });
    };




    $scope.openCommuteModal = function(){
       $scope.isCommuteModalOpen = true;
    };

    $scope.closeCommuteModal = function(){
       $scope.isCommuteModalOpen = false;
    };

    $scope.opts = {
        backdropFade: true,
        dialogFade:true,
        dialogOpenClass : 'fullscreen'
    };


}
CommuteTimeController.$inject = ['$scope','addressService','$http','userService','commuteService'];