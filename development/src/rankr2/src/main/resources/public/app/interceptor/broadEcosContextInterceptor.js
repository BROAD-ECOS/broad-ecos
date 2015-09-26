'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('broadEcosContextInterceptor', [ 'broadEcosService', function ( broadEcosService) {

        return {
            request: function($config) {

                var context = broadEcosService.context();

                if(context)
                {
                    $config.headers['broad-ecos-token'] = context.token;
                    $config.headers['broad-ecos-platform'] = context.platform;
                }

                return $config;
            }
        };
    }]);
})();