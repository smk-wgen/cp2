/**
 * Created by skunnumkal on 6/30/13.
 */
'use strict';
var servicesModule = angular.module('appServices', ['xmpl']);
servicesModule.factory('userService',function($http){
    var authenticatedUser = null;
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
         isRegistered  : function(id){
             var userPromise = $http.get('/checkExists/'+id);
             var result = null;
             var anotherPromise = userPromise.then(function(response){
                 var responseData = response.data;
                 if(responseData.isNew === false){
                     authenticatedUser = responseData.user;
                     console.log("User exists in our db..Now forward to dashboard");
                     result = true;
                 }
                 else{
                     console.log("New User..");
                     result = false;
                 }

                 return result;


             });

             return anotherPromise;

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


