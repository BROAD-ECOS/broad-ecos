angular.module('lopl')
    .factory('UserService', ['$cookieStore', function ($cookieStore) {

        _temp = {};

        return {
            isLoggedIn : function() {
                return $cookieStore.get('user') !=null;
            },
            getCurrent : function() {
                return $cookieStore.get('user');
            },
            setUserData : function(user) {
                $cookieStore.put('user', user);
            },
            setLoginRequestUserData: function(user) {
                _temp['userData'] = user;
            },
            getLoginRequestUserData: function() {
                var userData =  _temp['userData'];
                _temp[userData] = null;
                return userData;
            }
        };
    }]);