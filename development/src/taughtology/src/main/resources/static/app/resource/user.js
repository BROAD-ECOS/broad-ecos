'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('User', ['$resource', function ($resource) {
        return $resource('/users/:id', null,
            {
                'me': { method:'GET', params: {id:'me'} }
            });
    }]);
})();