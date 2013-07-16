/**
 * Created by skunnumkal on 7/15/13.
 */
function ProfileController($scope,$routeParams,userService,$http){
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

