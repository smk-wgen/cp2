/**
 * Created by skunnumkal on 7/15/13.
 */
function ProfileController($scope,userService,$http){
  'use strict';


    $scope.userCity = "New York";

    $scope.email = "abc@example.com";
    $scope.sex = "male";
    $scope.userEmployer = "Acme Inc";
    $scope.mobile = "000-000-0000";
    $scope.userService = userService;

    $scope.$watch('userService.currentUser',function(newValue, oldValue){


       if(newValue){
           $scope.fillUserForm();
       }

    });
    $scope.fillUserForm = function(){
        $scope.imageUrl = userService.currentUser.imageUrl;
        $scope.linkedInId = userService.currentUser.linkedInMemberId;
        $scope.userName = userService.currentUser.name;
        $scope.userEmployer = userService.currentUser.title || userService.currentUser.employer;
    };
    $scope.submitProfile = function(){



        $http.post("/user", { id: '',name: $scope.userName, employer: $scope.userEmployer,
            city: $scope.userCity, gender: $scope.sex , email : $scope.email , cell : $scope.mobile,
            imageUrl : $scope.imageUrl, linkedInId : $scope.linkedInId})
            .success(function(data){
                alert("Created user.Next fill address");


            });



    };
}

ProfileController.$inject = ['$scope','userService', '$http'];

