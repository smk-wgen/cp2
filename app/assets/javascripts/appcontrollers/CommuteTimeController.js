/**
 * Created by skunnumkal on 7/15/13.
 */
function CommuteTimeController($scope,addressService,$http,userService,commuteService){
    'use strict';
    $scope.startTime = 420;
    $scope.endTime = 570;
    $scope.isCommuteModalOpen = false;
    $scope.userService = userService;
    $scope.addressService = addressService;
    $scope.commutes = [];
    //$scope.currentUserId = null;
    $scope.label = 'To Work';
    $scope.showCommuteAdd = false;
    $scope.commuteStartTimes = [{'label' :'Before 6 am'},{'label':'6 am - 7 am'},{'label':'7 am - 8 am'},{'label':'8 am - 9 am'},
        {'label':'After 9 am'}]; //];
    $scope.timeInterval = '';


    $scope.$watch('addressService.userAddresses.length',function(newValue,oldValue){
        console.log("Watching ",$scope.addressService.userAddresses.length);
        if(newValue>=2){
            $scope.showCommuteAdd = true;
        }


        var user = $scope.userService.currentUser;
        if(user != undefined && user.id != undefined){
            addressService.getUserAddresses(user.id).then(function(response){
                $scope.ucas = response;
            });
        }


    });

    $scope.$watch('userService.currentUser.id',function(newValue,oldValue){
       if(newValue != undefined){
           getUserCommutes(newValue);
       }
    });



    $scope.startaddress = '';
    $scope.endaddress = '';

    var getUserCommutes = function(id){
        var commuteListPromise = commuteService.getUserCommutes(id);
        commuteListPromise.then(function(response){

            $scope.commutes = response;


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
        var user = userService.currentUser;
        var timeInterval = getTimeInterval();
        $scope.startTimeAsInt = timeInterval.start;
        $scope.endTimeAsInt = timeInterval.end;
        console.log("Stats",$scope.startTime,$scope.endTime,$scope.startaddress,$scope.startTimeAsInt,$scope.endTimeAsInt);
        var commute = {
         id: '',
         label : $scope.label,
         startAddress: $scope.startaddress.address,
         endAddress: $scope.endaddress.address,
         startTime: $scope.startTimeAsInt,
         endTime: $scope.endTimeAsInt ,
         userId : user.id
        };
        console.log(commute);
        var promisedCommute = commuteService.addUserCommute(commute,user.id);

        promisedCommute.then(function(response){
                console.log(response);
                //make rest of the page visible
                $scope.isCommuteModalOpen = false;
                $scope.commutes.push(response);
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