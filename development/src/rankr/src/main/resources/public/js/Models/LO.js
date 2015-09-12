'use strict';

angular.module('lopl')
.factory('LO', ['$resource', function($resource) {
    return $resource('/lo/:id', {id:'@id'});
}]);
