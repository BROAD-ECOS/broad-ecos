'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('$moment', [ function () {
        moment.locale('pt-BR');
        return moment;
    }]);
})();