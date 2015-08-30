'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Participant', ['$resource', function ($resource) {
        return $resource('/participants/:id', null,
            {
                'me': { method:'GET', params: {id:'me'} }
            });
    }]);
})();