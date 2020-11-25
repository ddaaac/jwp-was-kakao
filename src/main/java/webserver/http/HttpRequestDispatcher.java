package webserver.http;

import webserver.http.controller.Controller;
import webserver.http.controller.SignUpController;
import webserver.http.controller.TemplateController;

/**
 * HttpRequest 를 처리해줄 처리자를 찾아 request 를 전달한다. 처리자는 request 를 처리하고 response 까지 책임진다
 */
public class HttpRequestDispatcher {

    HttpRequestControllerMapper httpRequestControllerMapper = UriBaseHttpRequestControllerMapper.withDefaultMappings();
    {
        httpRequestControllerMapper.addMapping(
                new RegexpMapping("\\/.+\\.html", HttpMethod.GET, new TemplateController()),
                new RegexpMapping("\\/user\\/create", HttpMethod.POST, new SignUpController())
        );
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = httpRequestControllerMapper.getController(httpRequest);
        try {
            controller.execute(httpRequest, httpResponse);
        } catch (RuntimeException e) {
            httpResponse.setStatus(HttpStatus.x500_InternalServerError);
        }
    }

}
