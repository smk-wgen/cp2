/**
 * Created by skunnumkal on 7/15/13.
 */
function ProfileController($scope,userService,$http){
  'use strict';


    console.log("Profile Controller. Is user authenticated?", $scope.authenticated);
    $scope.isProfileModalOpen = $scope.authenticated && $scope.isNew;
    $scope.title = "Acme Inc";
    $scope.mobile = "000-000-0000";
    $scope.userService = userService;

    $scope.$watch('userService.currentUser.id',function(newValue, oldValue){


       if(newValue != undefined){
           console.log("Got an authenticated user from backend with id",newValue);

       }

    });

    $scope.submitProfile = function(){
        var newUser = userService.currentUser;
        if(newUser.imageUrl === undefined){
            newUser.imageUrl = '';
        }
        newUser.sex = $scope.sex;
        newUser.mobile = $scope.mobile;
        newUser.email = $scope.email;

        var userBody = { id: '',name: newUser.name, title: newUser.title,
            city: 'HardCode', gender: newUser.sex , email : newUser.email , cell : newUser.mobile,
            imageUrl : newUser.imageUrl, linkedInId : newUser.linkedInMemberId};
        console.log("Post Payload User",userBody);
        $http.post("/user",userBody )
            .success(function(response){
                console.log("Submit user success",response);
                userService.currentUser = response;
                $scope.sex = userService.currentUser.gender;
                $scope.title  = userService.currentUser.title;
                $scope.userName = userService.currentUser.name;
                $scope.isProfileModalOpen = false;


            }).error(function(errResponse){
                    console.error(errResponse);
                    alert("Sh1t blew up");
                });



    };
    $scope.closeAddressModal = function(){
        $scope.isProfileModalOpen = false;
    };
}

ProfileController.$inject = ['$scope','userService', '$http'];

