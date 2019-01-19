package xyz.wongs.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>Title:SwaggerApplication </p>
 * <p>@Description:
 * 1、@Api:修饰整个类，描述Controller
 * 2、@ApiOperation:描述一个类或者接口的方法
 * 3、@ApiParam:单个参数的描述
 * 4、@ApiModel:用对象来接收对象
 * 5、@ApiProperty:当用接收对象时，描述对象的字段
 * 6、@ApiResponse:HTTP响应其中一个描述	code：数字，例如400、message：信息，例如"请求参数没填好"、response：抛出异常的类
 * 7、@ApiResponses:HTTP整体响应的描述
 * 8、@ApiIgnore:使用该注解即忽略Api
 * 9、@ApiError:发生错误返回的信息
 * 10、@ApiImplicitParam:一个请求参数
 * i:paramType：参数放在哪个地方 [header-->请求参数的获取：@RequestHeader
 * query-->请求参数的获取：@RequestParam
 * path（用于restful接口）-->请求参数的获取：@PathVariable]
 * ii:name：参数名
 * iii:dataType：参数类型
 * iv:required：参数是否必须传
 * v:value：参数的意思
 * vi:defaultValue：参数的默认值
 * 11、@@ApiImplicitParams:多个请求参数
 * </p>
 * <p>Company: </p>
 * ----------------------------------------
 * \\	 /\/\   /\|	|/\   /\/\	 //
 * \\^^  ^^^  ^^|_|^^  ^^^  ^^//
 * \\^   ^^^  ^/Ϡ\^   ^^^  ^//
 * \\^ ^    ^/___\^    ^ ^//
 * \\^ ^^ ^//   \\^ ^^ ^//
 * \\	^^/(/     \)\^^ //
 * \\^'//       \\'^//
 * .==.   खान          .==.
 * ----------------------------------------
 *
 * @author: <a href="wcngs@qq.com">WCNGS</a>
 * @date: 2017年8月7日 上午10:48:58  *
 * @since JDK 1.7
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("xyz.wongs"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger构建API Doc")
                .description("Restfull")
                .version("1.0")
                .build();
    }
}
