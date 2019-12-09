from flask import Flask
import lxml.etree as ET


app = Flask(__name__)

@app.route("/")
def hello():
    dom = ET.parse('data.xml')
    xslt = ET.parse('transform1.xml')
    transform = ET.XSLT(xslt)
    newdom = transform(dom)
    return(ET.tostring(newdom, pretty_print=True))