from lxml import etree

tree = etree.parse('data.xml')

################################################ exercise 1 ##############################################
# 1. list all articles
print(tree.xpath('/catalog/articles/article/title/text()'))

# 2. search an author by a given name and list affiliations
print(tree.xpath('/catalog/authors/author[name/text()="Gambardella, Matthew"]/affiliations/affiliation/text()'))

# 3. list all conferences
print(tree.xpath('/catalog/categories/category[@type="conference"]/name/text()'))

# 4. list all journals
print(tree.xpath('/catalog/categories/category[@type="journal"]/name/text()'))

# 5. list latest articles starting with a prefix
print(tree.xpath('/catalog/articles/article/title[starts-with(text(), "XML")]/text()'))

# 6. list authors with West University of Timisoara affiliation
print(tree.xpath('/catalog/authors/author/affiliations[contains(affiliation, "West University of Timisoara")]/../name/text()'))

# 7. list all category types
print(tree.xpath('/catalog/categories/category/@type'))

# 8. search by id
print(tree.xpath('/catalog/*/*[@id="au02"]/name/text()'))

# 9. search articles by departament
print(tree.xpath('/catalog/articles/article[faculty/text() = "Computer Science"]/title/text()'))

# 10. list date and isbn concatenated for each article
print(tree.xpath('concat("ISBN: ", /catalog/articles/article/isbn/text(), " DATE: ", /catalog/articles/article/publish_date/text())'))

################################################ exercise 2 ##############################################
# list of articles for different categories
category_ids = tree.xpath('/catalog/categories/category/@id')
for category_id in category_ids:
    categ_name = tree.xpath('/catalog/categories/category[@id = "{0}"]/@type'.format(category_id))
    print("Category: {0}".format(categ_name))
    print(tree.xpath('/catalog/articles/article[@categoryId = "{0}"]/title/text()'.format(category_id)))
