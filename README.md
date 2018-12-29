## api-security
### 使用步骤
- 配置文件加入以下属性
```yaml
#启用验签(默认为true)
api.hmac.enbaled=true
#对以下url进行验签(默认为/api/*)
api.hmac.urlPatterns[0]=/hello
#从头部参数为X-sign的名获取客户端验签后的值(默认值为Authorization)
api.hmac.headerName=X-Sign
#从url参数为api_key的获取产品标识(默认值为product_key)
api.hmac.productUrlParamName=api_key
#如果激活application-dev.properties or application-dev.yml配置文件将不进行验签
api.hmac.excludeProfiles[0]=dev
```
- 支持多产品，用户必须重写ProductProvider类，为不同的产品提供不同的加密密钥
```java
@Component
public class ProductProviderImpl implements ProductProvider {
    private Map<String,Product> products=Maps.newConcurrentMap();
    {
        products.put("facebook",new Product("facebook", "@#SD$T%541DFS4"));
        products.put("twitter",new Product("twitter", "sadlksdf6465"));
    }
    @Override
    public Product getProduct(String key) {
        return products.get(key);
    }
}
```
- 验证
```jshelllanguage
curl -H "X-Sign:SlpXeBkVNQ3UPUDTGktbwTBYa3YVGcWJuYEtsTLMEac"  http://127.0.0.1/hello?api_key=facebook
```