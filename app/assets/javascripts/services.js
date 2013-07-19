/**
 * Created by skunnumkal on 6/30/13.
 */
'use strict';
var servicesModule = angular.module('appServices', ['xmpl']);
servicesModule.factory('userService',function($http,fromoutside){
    var authenticatedUser;
    fromoutside.then(function(obj) {
        authenticatedUser = obj;
    });
      return {
         currentUser : authenticatedUser,
         getUser : function(id){
             console.log("Call the backend for the user");
         },
         getUserCommutes : function(id) {
             console.log("Get users commutes");
         },
         getUserAddresses : function(id){
             console.log("Get user addresses");
         },
         authenticate : function(id){
             var userPromise = $http.get('/checkExists/'+id);
             userPromise.then(function(response){
                 console.log(response.data);
                 var responseData = response.data;
                 if(responseData.isNew=='false'){
                     authenticatedUser.id = responseData.user.id;

                     console.log("User exists in our db..Now forward to dashboard");
                     $window.location.href='/dashboard/'+responseData.user.id;
                 }
                 else{
                     console.log("New User..");
                     $window.location.href ='/wizard';
                 }




             });
         }
      };
});
servicesModule.factory('addressService',function($http){
    var myAddresses = [];
    function getAddresses (id) {
        return $http.get('/addresses/'+id);

    };
    function addAddress(address){
        myAddresses.push(address);
    }
    return {
             getUserAddresses: getAddresses,
             addUserAddress : addAddress};
});
servicesModule.factory('commuteService',function($http){
     return{
         getUserCommutes : function(id){
             return $http.get('/usercommutes/'+id);
         }
     };
});


