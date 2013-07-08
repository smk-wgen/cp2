/**
 * Created by skunnumkal on 6/30/13.
 */

angular.module('profiler.controllers', ['appServices']).
    //controller('ChatCtrl', function ($scope, $http, chatModel) {
    //
    controller('AddressController',function($scope,$http,addressService){
       'use strict';
        $scope.$watch('addressService.myAddresses',function(newValue,oldValue){
                 console.log('Addresses changed');
        });
        $scope.addresses = addressService.getUserAddresses();
        $scope.userId = "1"; //get this from a service
        $scope.line1 = "Street name";
        $scope.label = "Label of Address";
        $scope.line2 = '';
        $scope.city = "Chewburg";
        $scope.state = "PA";
        $scope.zip = "10944";
        $scope.addAddress = function(){
            $http.post("/address", { label: $scope.label, line1: $scope.line1,
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
    }).
    controller('ProfileController',function($scope,$http){
      'use strict';

       $scope.userName = "Name From Linkedin";
       $scope.userEmployer = "Employer (from LinkedIn)";
       $scope.userCity = "City (from LinkedIn)";
       $scope.imageUrl = "imgsrc";
       $scope.linkedInId = "www.linkedin.com";
       $scope.email = "abc@example.com";
       $scope.sex = "male";
       $scope.mobile = "000-000-0000";

       $scope.submitProfile = function(){


         console.log($scope.user);
           $http.post("/user", { id: '',name: $scope.userName, employer: $scope.userEmployer,
               city: $scope.userCity, gender: $scope.sex , email : $scope.email , cell : $scope.mobile,
           imageUrl : $scope.imageUrl, linkedInId : $scope.linkedInId})
               .success(function(data){
                   console.log(data);
                   //make rest of the page visible

               });



    };

    }).controller('CommuteTimeController',function($scope,$http,addressService){
        'use strict';
         $scope.startTime = startTime || 420;
         $scope.endTime = endTime || 570;
         $scope.userId = 1;


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
                startTime: $scope.startTime, endTime: $scope.endTime , userId : $scope.userId })
                .success(function(data){
                    console.log(data);
                    //make rest of the page visible

                });
        };

    });


