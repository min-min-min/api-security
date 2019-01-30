## api-security
### 使用步骤
- 验签属性设置
```yaml
#启用验签
api.hmac.enbaled=true
#对以下url进行验签(默认为/api/*)
api.hmac.urlPatterns[0]=/hello
#从请求头参数为X-sign的名获取客户端验签后的值(默认值为Authorization)
api.hmac.headerName=X-Sign
#如果激活application-dev.properties or application-dev.yml配置文件将不进行验签
api.hmac.excludeProfiles[0]=dev
```
- 解密属性设置
```yaml
#启用解密
api.decrypt.enabled=true
#需要解密的url(默认为/api/*)
api.decrypt.url-patterns=/*
#如果激活application-dev.properties or application-dev.yml配置文件将不进行解密
api.decrypt.excludeProfiles[0]=dev
```
- 解密属性设置
```yaml
#启用加密
api.encrypt.enabled=true
#需要加密的url(默认为/api/*)
api.encrypt.url-patterns=/*
#如果激活application-dev.properties or application-dev.yml配置文件将不进行加密
api.encrypt.excludeProfiles[0]=dev
```
- 支持多产品，用户必须实现`ProductProvider`接口，为不同的产品提供不同的加密密钥
```java
@Component
public class ProductProviderImpl implements ProductProvider {

    @Override
    public String getHmacKey(HttpServletRequest request) {
        return "SDFlkjsnm.65";
    }

    @Override
    public String getEncryptKey(HttpServletRequest request) {
        return "GH354XASf513ss";
    }

    @Override
    public String getDecryptKey(HttpServletRequest request) {
        return "GH354XASf513ss";
    }
}
```
- 验证
```jshelllanguage
curl -H "X-Sign:SlpXeBkVNQ3UPUDTGktbwTBYa3YVGcWJuYEtsTLMEac"  http://127.0.0.1/hello?api_key=facebook
```
### tips
* 选择实现`SignBuilder`接口来实现自己的验签规则
* 选择实现`AbstractSecretProvider`类实现自己的加解密规则