import React from "react";
import { Box, Grommet, Heading } from "grommet";

var convert = require('xml-js');

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
    }
  }
};

const getXML = () => {
  fetch('http://localhost:9090/xml-catalog', {method: 'GET'})
  .then(response => response.text())
  .then(xmlStr => {
    return convert.xml2json(xmlStr, {compact: true});
  } )
  .then(jsonStr => JSON.parse(jsonStr))
  .then(json => console.log(json))
}

const App = () => {

  getXML();
  return (
    <Grommet theme={theme} full>
      <Box fill>
        <AppBar>Kings of Music</AppBar>

        <Box direction="row" flex overflow={{ horizontal: "hidden" }}>
          <Box flex align="center" justify="center">
            app body
          </Box>
          <Box
            width="medium"
            background="light-2"
            elevation="small"
            align="center"
            justify="center"
          >
            sidebar
          </Box>
        </Box>
      </Box>
    </Grommet>
  );
}

export default App;
