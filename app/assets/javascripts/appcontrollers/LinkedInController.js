/**
 * Created by skunnumkal on 7/18/13.
 */
function LinkedInController($scope,userService){

    userService.register($scope);
    console.log("User Is",userService.currentUser);
    $scope.$watch('userService.currentUser != null',function(){
         console.log("Value changed");
         console.log(userService.currentUser);
    });


}

LinkedInController.$inject = ['$scope','userService'];
