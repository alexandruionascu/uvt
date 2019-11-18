import xml.sax
from xml.dom import minidom

class ArticleHandler(xml.sax.ContentHandler):

    ARTICLE_TAG = 'article'

    def __init__(self, searchKeys=[], customFilter=()):
        self.searchKeys = searchKeys
        self.filter = customFilter
        self.searchDepth = 0
        self.filterDepth = 0
        self.matchedFilter = True if not self.filter else False
        self.searchValue = None

    def startElement(self, tag, attributes):
        if tag == ArticleHandler.ARTICLE_TAG:
            # reset filter
            self.matchedFilter = True if not self.filter else False
            self.searchValue = None

        if tag in self.searchKeys:
            self.searchDepth += 1

        if tag in self.filter:
            self.filterDepth += 1

    def endElement(self, tag):
        if tag in self.searchKeys:
            self.searchDepth -= 1
        if tag in self.filter:
            self.filterDepth -= 1

        if tag == ArticleHandler.ARTICLE_TAG and self.matchedFilter and self.searchValue:
            print(self.searchValue)

    def characters(self, content):
        if self.searchDepth == len(self.searchKeys):
            self.searchValue = content

        if self.filterDepth == len(self.filter) - 1 and content == self.filter[-1]:
            self.matchedFilter = True

def br():
    print('-' * 42)

if __name__ == '__main__':
    DATA_PATH = 'data.xml'
    # SAX Parser
    parser = xml.sax.make_parser()
    parser.setFeature(xml.sax.handler.feature_namespaces, 0)

    # list of articles, for a well specified author
    Handler = ArticleHandler(
        searchKeys=['articles', 'article', 'title'],
        customFilter=('articles', 'article', 'author', 'name', 'Gambardella, Matthew')
    )
    print('SAX parse for filter {0}'.format(Handler.filter))
    parser.setContentHandler(Handler)
    parser.parse(DATA_PATH)

    br()

    # list of articles for a Faculty/Department
    Handler = ArticleHandler(
        searchKeys=['articles', 'article', 'title'],
        customFilter=('articles', 'article', 'faculty', 'Computer Science')
    )
    print('SAX parse for filter {0}'.format(Handler.filter))
    parser.setContentHandler(Handler)
    parser.parse(DATA_PATH)
    br()
    # DOM Parser
    doc = minidom.parse(DATA_PATH)
    # conference, journal, volumes, etc.
    types = set([category.attributes['type'].nodeValue for category in doc.getElementsByTagName('category')])
    print('DOM parsing, types: {0}'.format(types))
    articles = doc.getElementsByTagName('article')
    for type in types:
        print("Searching for '{0}'".format(type))
        for article in articles:
            match = False
            categories = article.getElementsByTagName('category')
            for category in categories:
                if category.attributes['type'].nodeValue == type:
                    match = True
            if match:
                title = article.getElementsByTagName('title')[0]
                print(title.firstChild.nodeValue) 
        br()