/**
 * Created by skunnumkal on 6/22/13.
 */
angular.module('xmpl', [/*, 'xmpl.directive', 'xmpl.filter'*/])
    /*.config(function($routeProvider) {
        $routeProvider.when('/add-profile', {
            templateUrl: '/assets/angular/profile-form.html'  ,
            controller : 'ProfileController'
        })
    })*/;

angular.module('profiler',[]);
// A Controller for your app
var ProfileController = function($scope) {
    'use strict';
    console.log("In the profile controller");
}
ProfileController.$inject = ['$scope'];
