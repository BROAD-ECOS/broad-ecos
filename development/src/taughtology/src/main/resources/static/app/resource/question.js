'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Question', ['$resource', function ($resource) {
        return $resource('/questions/:id', null,
            {
                'random': { method:'GET', params: {id:'random'} }
            });
    }]);
})();