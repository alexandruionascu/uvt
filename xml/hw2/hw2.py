import xmlschema

xsd = xmlschema.XMLSchema('schema.xsd')
result = xsd.validate('data.xml')
if not result:
    print("Document is valid.")