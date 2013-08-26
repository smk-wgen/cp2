/**
 * Created by skunnumkal on 7/15/13.
 */
function ProfileController($scope,userService,$http){
  'use strict';


    $scope.userCity = "New York";
    console.log("Profile Controller", $scope.authenticated,$scope.isNew);
    $scope.isProfileModalOpen = $scope.authenticated && $scope.isNew;
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
    $scope.$watch('userService.currentUser.isNew',function(newValue,oldValue){
       console.log("In watch",newValue,$scope.authenticated);
       if(newValue !== undefined && newValue == true && $scope.authenticated){
             $scope.isProfileModalOpen = true;
       }
    });
    $scope.fillUserForm = function(){
        $scope.imageUrl = userService.currentUser.imageUrl || '/assets/images/personal_user_128.png';
        $scope.linkedInId = userService.currentUser.linkedInMemberId;
        $scope.userName = userService.currentUser.name;
        $scope.userEmployer = userService.currentUser.title || userService.currentUser.employer;
    };
    $scope.submitProfile = function(){
        if($scope.imageUrl === undefined){
            $scope.imageUrl = '';
        }
        var userBody = { id: '',name: $scope.userName, employer: $scope.userEmployer,
            city: $scope.userCity, gender: $scope.sex , email : $scope.email , cell : $scope.mobile,
            imageUrl : $scope.imageUrl, linkedInId : $scope.linkedInId};
        console.log("Post Payload User",userBody);
        $http.post("/user",userBody )
            .success(function(response){
                console.log("Submmit user success",response.data);
                userService.currentUser = response.data;
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

