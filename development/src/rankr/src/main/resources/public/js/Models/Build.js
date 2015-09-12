'use strict';

angular.module('lopl')
    .factory('ProductBuild', ['$resource', function($resource) {
        return $resource('/pl/build/:id', {id:'@id'});
    }]);

