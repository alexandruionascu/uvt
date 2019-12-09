from flask import Flask
import lxml.etree as ET


app = Flask(__name__)
dom = ET.parse('data.xml')

def renderTransform(transform_name):
    xslt = ET.parse(transform_name)
    transform = ET.XSLT(xslt)
    newdom = transform(dom)
    return(ET.tostring(newdom, pretty_print=True))

@app.route("/")
def T1():
    return renderTransform('transform1.xml')

@app.route("/articles")
def T2():
    return renderTransform('transform2.xml')

@app.route("/authors")
def T3():
    return renderTransform("transform3.xml")

@app.route("/ids")
def T4():
    return renderTransform("transform4.xml")

@app.route("/categories")
def T5():
    return renderTransform("transform5.xml")

    