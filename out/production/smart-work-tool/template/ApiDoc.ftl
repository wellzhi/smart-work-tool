### 接口描述
${desc}

### URI
此接口${(needToken)?string('需要','不需要')}`token`

```javascript
POST ${apiUrl}
```

### RequestBody
```javascript
${apiReq}
```

### ResponseBody
```javascript
${apiRes}
```

### Quote
<#list apiViews as apiView>
  - [${apiView}](Struct-${apiView})
</#list>



${apiItemMd}