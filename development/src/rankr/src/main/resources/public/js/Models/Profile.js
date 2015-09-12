'use strict';

angular.module('lopl')
.factory('Profile', ['$resource', function($resource) {
    return $resource('/profiles/:id', {id:'@id'});
}]);
