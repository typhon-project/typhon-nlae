/*******************************************************************************
 * Copyright (C) 2020 Edge Hill University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package typhon.nlae.rest.api;


	import springfox.documentation.builders.ApiInfoBuilder;
	import springfox.documentation.builders.RequestHandlerSelectors;
	import springfox.documentation.service.ApiInfo;
	import springfox.documentation.service.Contact;
	import springfox.documentation.spi.DocumentationType;
	import springfox.documentation.spring.web.plugins.Docket;

	public class DocumentationConfiguration {

		public DocumentationConfiguration() {

			configuration();

		}

		public Docket getConfiguration() {

			return configuration();
		}

		private Docket configuration() {

			Docket configuration = new Docket(DocumentationType.SWAGGER_2).select()
					.apis(RequestHandlerSelectors.basePackage(ApiDescription.basePackage))
					.build().apiInfo(apiDescription());

			return configuration;
		}

		private ApiInfo apiDescription() {

			ApiInfoBuilder info = new ApiInfoBuilder();
			info.title(ApiDescription.title);
			info.description(ApiDescription.description);
			info.version(ApiDescription.version);
			info.license(ApiDescription.license);
			info.licenseUrl(ApiDescription.licenseUrl);
			
			info.contact(new Contact(ApiDescription.contactName,
					ApiDescription.contactUrl,
					ApiDescription.contactEmail));

			return info.build();
		}

}
