'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('$moment', [ function () {
        moment.locale('pt-BR');
        return moment;
    }]);
})();