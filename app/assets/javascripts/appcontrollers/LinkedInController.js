/**
 * Created by skunnumkal on 7/18/13.
 */
function LinkedInController($scope,userService){
    $scope.authenticated = false;
    $scope.isNew = true;

    $scope.setAuthenticatedUser = function(liUser){
        $scope.authenticated = true;

        userService.currentUser = liUser;
        console.log("Setting basic user object after Linkedin Auth",userService.currentUser);
        var someResultPromise = userService.isRegistered(liUser.linkedInMemberId);
           someResultPromise.then(function(result){
               console.log("Result of 2nd promise",result," So user exists =",result);
               if(result){
                   $scope.isNew = false;
               }

           });


    };



}

LinkedInController.$inject = ['$scope','userService'];
