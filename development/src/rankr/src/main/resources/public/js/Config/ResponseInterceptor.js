/**
 * Created by Maikel Rivero Dorta mriverodorta@gmail.com on 7/08/14.
 */
"use strict";
angular.module('lopl')
    .factory('ResponseInterceptor', ['$location', '$q', function($location, $q) {

        
        var responseInterceptor = {
            'response': function(response) {
              return response;
            },

           'responseError': function(response) {
                if(response.status === 401) {
                    $location.path('/unautorized');
                }

                if(response.status === 404) {
                    $location.path('/not-found');
                }

                return $q.reject(response);
            }
        };


        return responseInterceptor;
    }]);
