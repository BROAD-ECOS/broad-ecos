'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('userService', ['User', '$q', function (User, $q) {

        var _userService = {};
        _userService.me = {};

        var userService = {};
        userService.contextUser = function () {
            var promise;

            if (_userService.me.id) {
                var deferred = $q.defer();
                deferred.resolve(_userService.me);
                promise = deferred.promise;
            } else {
                promise = User.me().$promise;
            }

            return promise;

        };

        return userService;

    }]);
})();