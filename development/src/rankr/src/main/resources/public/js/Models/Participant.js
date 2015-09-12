'use strict';

angular.module('lopl')
.factory('Participant', ['$resource', function($resource) {
    return $resource('/participants/:id', {id:'@id'});
}]);
