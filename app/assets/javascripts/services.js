/**
 * Created by skunnumkal on 6/30/13.
 */
'use strict';
var servicesModule = angular.module('appServices', []);
servicesModule.factory('userService',function($http){

      var aUserService = {
         currentUser : '',
         getUser : function(id){
             console.log("Call the backend for the user");
         },
          registerOrLogin  : function(user){
             var userPromise = $http.post('/user',aUserService.currentUser);
             var result = null;
             var anotherPromise = userPromise.then(function(response){
                 var responseData = response.data;
                 console.log("Response from backEnd for user",responseData);
                 aUserService.currentUser = responseData;

                 console.log("Setting authenticated (currentUser) as",aUserService.currentUser);

                 result = true;



                 return result;


             },function(errResponse){
                 console.error(errResponse);
                 result = false;
                 return result;
             });

             return anotherPromise;

         }
      };
    return aUserService;
});
servicesModule.factory('addressService',function($http,$q){
    var anAddressService = {
             userAddresses : [],
             getUserAddresses: function(id){
                 //Temp hack for prototype
                 var deferredAddresses = $q.defer();
                 //deferredAddresses.resolve(anAddressService.userAddresses);
                 $http.get('/addresses/'+id).then(function(response){
                      anAddressService.userAddresses = response.data;
                      deferredAddresses.resolve(response.data);
                 },function(errResponse){
                     console.error(errResponse.data);
                     deferredAddresses.reject(errResponse.data);
                 });
                 return deferredAddresses.promise;

             },
             addUserAddress : function(address){
                 console.log("Create address payload",address);
                 var deferredAddress = $q.defer();
                 $http.post("/address", address).then(function(response){
                         //Temp hack for prototype
                         address.id = anAddressService.userAddresses.length+1;
                         anAddressService.userAddresses.push(address)
                         deferredAddress.resolve(address)
                         //var addedAddress = response.data;
                         //anAddressService.userAddresses.push(addedAddress);
                         //deferredAddress.resolve(addedAddress);
                     },
                     function(errResponse){
                         console.error(errResponse.data);
                         deferredAddress.reject(errResponse.data);
                     });
                 return deferredAddress.promise;
             }
    };
    return anAddressService;
});
servicesModule.factory('commuteService',function($http,$q){
     var aCommuteService = {
         userCommutes : [],
         getUserCommutes : function(id){
             var deferredCommutes = $q.defer();
             deferredCommutes.resolve(aCommuteService.userCommutes);
//             $http.get('/usercommutes/'+id).then(function(response){
//                 deferredCommutes.resolve(response.data);
//             },function(errResponse){
//                 console.error(errResponse);
//                 deferredCommutes.reject(errResponse.data);
//             });
             return deferredCommutes.promise;
         } ,
         addUserCommute : function(commute){
             var deferredCommute = $q.defer();

             $http.post("/usercommute", commute).then(function(response){
                 commute.id = aCommuteService.userCommutes.length+1;
                 aCommuteService.userCommutes.push(commute);
                 deferredCommute.resolve(commute);
                 //var aCommute = response.data;
                 //aCommuteService.userCommutes.push(aCommute);
                 //deferredCommute.resolve(aCommute);
             },function(errResponse){
                 console.error(errResponse);
                 deferredCommute.reject(errResponse.data);
             });
             return deferredCommute.promise;
         }
     }
      return aCommuteService;
});


