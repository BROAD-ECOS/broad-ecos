'use strict';

angular.module('lopl')
.factory('Conquest', ['$resource', function($resource) {
    return $resource('/conquests/:id', {id:'@id'});
}]);
