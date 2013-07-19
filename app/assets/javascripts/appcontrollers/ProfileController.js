/**
 * Created by skunnumkal on 7/15/13.
 */
function ProfileController($scope,$routeParams,userService,$http){
  'use strict';
    userService.register($scope);
    $scope.userName = userService.authenticatedUser.name;
    $scope.userEmployer = userService.authenticatedUser.title;
    $scope.userCity = "New York";
    $scope.imageUrl = userService.authenticatedUser.imageUrl;
    $scope.linkedInId = userService.authenticatedUser.linkedInMemberId;
    $scope.email = "abc@example.com";
    $scope.sex = "male";
    $scope.mobile = "000-000-0000";

    $scope.submitProfile = function(){



        $http.post("/user", { id: '',name: $scope.userName, employer: $scope.userEmployer,
            city: $scope.userCity, gender: $scope.sex , email : $scope.email , cell : $scope.mobile,
            imageUrl : $scope.imageUrl, linkedInId : $scope.linkedInId})
            .success(function(data){
                console.log(data);
                //make rest of the page visible

            });



    };
}

ProfileController.$inject = ['$scope','$routeParams', 'userService', '$http'];

