import React from "react";
import {
  Box,
  Grommet,
  List,
  Heading,
  Form,
  FormField,
  Button,
  Tab,
  Tabs,
  Text
} from "grommet";

import { Close } from "grommet-icons";

var convert = require("xml-js");

const AppBar = props => (
  <Box
    tag="header"
    direction="row"
    align="center"
    justify="between"
    background="brand"
    pad={{ left: "medium", right: "small", vertical: "small" }}
    elevation="medium"
    style={{ zIndex: "1" }}
    {...props}
  />
);

const theme = {
  global: {
    font: {
      family: "Roboto",
      size: "18px",
      height: "20px"
    },
    animation: {
      duration: "1s",
      jiggle: { duration: "0.1s" }
    }
  }
};

const fetchHtml = xslt => {
  return fetch("http://localhost:9090/" + xslt).then(response =>
    response.text()
  );
};

const getCatalog = () => {
  return fetch("http://localhost:9090/xml-catalog", { method: "GET" })
    .then(response => response.text())
    .then(xmlStr => {
      return convert.xml2json(xmlStr, { compact: true });
    })
    .then(jsonStr => JSON.parse(jsonStr));
};

const removeSong = id => {
  return fetch("http://localhost:9090/song", {
    method: "DELETE",
    body: JSON.stringify({ id: id })
  });
};

const postSong = songData => {
  return fetch("http://localhost:9090/song", {
    method: "POST",
    body: JSON.stringify(songData)
  });
};

const putSong = songData => {
  return fetch("http://localhost:9090/song", {
    method: "PUT",
    body: JSON.stringify(songData)
  });
};

const idsToAuthors = (csv, authors) => {
  const ids = csv.split(",").map(x => x.trim());
  let aut = [];
  for (let id of ids) {
    for (let author of authors) {
      if (id === author.id._text) {
        aut.push(author.name._text);
      }
    }
  }

  return aut.join(", ");
};

const App = () => {
  const [catalog, setCatalog] = React.useState(null);
  const songs = catalog ? catalog.songs.song : [];
  const genres = catalog ? catalog.genres.genre : [];
  const authors = catalog ? catalog.authors.author : [];
  const [listHtml, setListHtml] = React.useState("");
  const [genreHtml, setGenreHtml] = React.useState("");

  console.log(songs);

  React.useEffect(() => {
    if (!catalog) {
      getCatalog().then(data => setCatalog(data.catalog));
    }

    if (listHtml.length === 0) {
      fetchHtml("all").then(data => setListHtml(data));
    }

    if (genreHtml.length === 0) {
      fetchHtml("genres").then(data => setGenreHtml(data));
    }
  });

  return (
    <Grommet theme={theme} full>
      <Box fill>
        <AppBar>Music Library</AppBar>

        <Box margin="large" pad="large" flex align="center" justify="center">
          <Tabs>
            <Tab title="Songs">
              <Box flex align="center" justify="center">
                <Heading>List of Songs</Heading>
                <List
                  primaryKey={(song, i) => (
                    <Text key={i}>
                      #{song.id} {idsToAuthors(song.authorids, authors)} -{" "}
                      {song.title} | {song.publishdate}
                    </Text>
                  )}
                  secondaryKey={(song, i) => (
                    <Box key={song.id}>
                      <Button
                        key={i}
                        icon={<Close />}
                        onClick={() => {
                          removeSong(song.id).then(() =>
                            window.location.reload()
                          );
                        }}
                        primary
                      />
                    </Box>
                  )}
                  data={songs.map(x => ({
                    title: x.title._text,
                    publishdate: x.publishdate._text,
                    id: x.id._text,
                    authorids: x.authorids._text
                  }))}
                />
              </Box>
            </Tab>
            <Tab title="Authors">
              <Box flex align="center" justify="center">
                <Heading>List of Authors</Heading>
                <List
                  primaryKey={author => (
                    <Text>
                      #{author.id} {author.name}{" "}
                    </Text>
                  )}
                  data={authors.map(x => ({
                    id: x.id._text,
                    name: x.name._text
                  }))}
                />
              </Box>
            </Tab>
            <Tab title="List All - XPath">
              <Box flex align="center" justify="center">
                <Text style={{fontFamily: theme.global.font.family}} dangerouslySetInnerHTML={{ __html: listHtml }} />
              </Box>
            </Tab>
            <Tab title="Count Genres - XPath">
              <Box flex align="center" justify="center">
                <div dangerouslySetInnerHTML={{ __html: genreHtml }} />
              </Box>
            </Tab>
            <Tab title="Genres">
              <Box flex align="center" justify="center">
                <Heading>List of Genres</Heading>
                <List
                  primaryKey="name"
                  secondaryKey="id"
                  data={genres.map(x => ({
                    id: x.id._text,
                    name: x.name._text
                  }))}
                ></List>
              </Box>
            </Tab>
            <Tab title="Add Song">
              <Form
                onSubmit={event =>
                  postSong(event.value).then(() => window.location.reload())
                }
                margin="large"
                pad="large"
              >
                <Heading>Add Song</Heading>
                <FormField name="title" label="title" required />
                <FormField name="id" label="Id" required />
                <FormField name="publishdate" label="Publish Date" required />
                <FormField
                  name="authorids"
                  label="Author Ids in CSV"
                  required
                />
                <FormField name="genreid" label="Genre Id" required />
                <Button type="submit" primary label="Submit" />
              </Form>
              <br /> <br />
            </Tab>

            <Tab title="Edit Song">
              <Form
                onSubmit={event =>
                  putSong(event.value).then(() => window.location.reload())
                }
                margin="large"
                pad="large"
              >
                <Heading>Edit Song</Heading>
                <FormField name="title" label="title" required />
                <FormField name="id" label="Id" required />
                <FormField name="publishdate" label="Publish Date" required />
                <FormField
                  name="authorids"
                  label="Author Ids in CSV"
                  required
                />
                <FormField name="genreid" label="Genre Id" required />
                <Button type="submit" primary label="Submit" />
              </Form>
              <br /> <br />
            </Tab>
          </Tabs>
        </Box>
      </Box>
    </Grommet>
  );
};

export default App;
