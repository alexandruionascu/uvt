import React from "react";
import { Box, Grommet, List, Heading, Form, FormField, Button } from "grommet";

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
    colors: {
      brand: "#228BE6"
    },
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

const getCatalog = () => {
  return fetch("http://localhost:9090/xml-catalog", { method: "GET" })
    .then(response => response.text())
    .then(xmlStr => {
      return convert.xml2json(xmlStr, { compact: true });
    })
    .then(jsonStr => JSON.parse(jsonStr));
};

const App = () => {
  const [catalog, setCatalog] = React.useState(null);
  const songs = catalog ? catalog.songs.song : [];
  const genres = catalog ? catalog.genres.genre : [];
  const authors = catalog ? catalog.authors.author : [];

  console.log(songs);

  React.useEffect(() => {
    if (!catalog) {
      getCatalog().then(data => setCatalog(data.catalog));
    }
  });

  return (
    <Grommet theme={theme} full>
      <Box fill>
        <AppBar>Kings of Music</AppBar>

        <Box
          direction="row"
          pad="large"
          flex
          overflow={{ horizontal: "hidden" }}
        >
          <Box flex align="center" justify="center" pad="large">
            <Heading>List of Songs</Heading>
            <List
              primaryKey="name"
              secondaryKey="date"
              data={songs.map(x => ({
                name: x.title._text,
                date: x.publishdate._text
              }))}
            />

            <Heading>List of Authors</Heading>
            <List
              primaryKey="name"
              secondaryKey="id"
              data={authors.map(x => ({
                id: x.id._text,
                name: x.name._text
              }))}
            />

            <Heading>List of Genres</Heading>
            <List
              primaryKey="name"
              secondaryKey="id"
              data={genres.map(x => ({
                id: x.id._text,
                name: x.name._text
              }))}
            />
          </Box>

          <Form>
            <Heading>Add Song</Heading>
            <FormField name="name" label="Name" />
            <FormField name="id" label="Id" />
            <FormField name="publishdate" label="Publish Date" />
            <FormField name="authors" label="Author Id's in CSV" />
            <FormField name="genreid" label="Genre Id" />
            <Button type="submit" primary label="Submit" />
          </Form>
        </Box>
      </Box>
    </Grommet>
  );
};

export default App;
