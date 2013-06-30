<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:if test="${param.loginResult eq 'FAILED'}">
    <script type="text/javascript">
        $(document).ready(function () {
            $.Dialog({
                'title': 'Вход',
                'content': 'Введены неправильные логин или пароль.',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'OK': {
                        'action': function () {
                            var url = window.location.href.replace('loginResult=FAILED', '');
                            if (url.indexOf('?') == url.length - 1) {
                                url = url.substr(0, url.length - 1);
                            }
                            window.location.replace(url);
                        }
                    }
                }
            });
        });
    </script>
</c:if>
<a href="<s:url action="tournament-list"/>">
    <div class="tile double bg-color-green">
        <div class="tile-content">
            <h4>Список турниров</h4>
            <p>Переходите к редактированию турниров</p>
        </div>
    </div>
</a>