/**
 * Created by skunnumkal on 6/30/13.
 */

angular.module('profiler.controllers', []).
    //controller('ChatCtrl', function ($scope, $http, chatModel) {
    //
    controller('AddressController',function($scope,$http){
       'use strict';
        $scope.addresses = [];
        $scope.userId = "1"; //get this from a service
        $scope.addAddress = function(){
            $http.post("/address", { name: $scope.address.name, addressLine1: $scope.address.line1,
                addressLine2: $scope.address.line1, city: $scope.address.city , zip : $scope.address.line1 ,
                userId : $scope.userId})
                .success(function(data){
                    console.log(data);
                    $scope.addresses.push({name:$scope.address.name});
                    //make rest of the page visible

                });
        };
    }).
    controller('ProfileController',function($scope,$http){
      'use strict';
       console.log('In profile controller');
       $scope.userName = "Name From Linkedin";
       $scope.userEmployer = "Employer (from LinkedIn)";
       $scope.userCity = "City (from LinkedIn)";
       $scope.imageUrl = "imgsrc";
       $scope.linkedInId = "www.linkedin.com";
       $scope.email = "abc@example.com";
       $scope.sex = "male";
       $scope.mobile = "000-000-0000";

       $scope.submitProfile = function(){

         console.log('In profile submit');
         console.log($scope.user);
           $http.post("/user", { id: '',name: $scope.userName, employer: $scope.userEmployer,
               city: $scope.userCity, gender: $scope.sex , email : $scope.email , cell : $scope.mobile,
           imageUrl : $scope.imageUrl, linkedInId : $scope.linkedInId})
               .success(function(data){
                   console.log(data);
                   //make rest of the page visible

               });



    };

    });


