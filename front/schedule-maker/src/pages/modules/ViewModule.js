import {
  Button,
  Grid,
  List,
  ListItem,
  ListItemText,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export function ViewModule() {
  const [module, setModule] = useState({});
  const params = useParams();
  const [subjects, setSubjects] = useState([]);

  useEffect(() => {
    fetch("api/v1/modules/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setModule(data);
      });
  }, []);

  useEffect(() => {
    fetch("api/v1/subjects/module/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setSubjects(data);
      });
  }, []);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={12} marginTop={4}>
            <header>
              <h1>{module.name}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {module.modifiedDate}
              </span>
            </header>
          </Grid>
          <Grid item sm={12}>
            <h4>Modulio kodas</h4>
            <p>{module.number}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Modulio pavadinimas</h4>
            <p>{module.name}</p>
          </Grid>
          <Grid item sm={10}>
            <h4>Priskirti dalykai</h4>
          </Grid>
          <Grid item sm={2}>
            <Link to="/subjects/create">
              <Stack direction="row" justifyContent="flex-end">
                <Button variant="contained">Pridėti naują</Button>
              </Stack>
            </Link>
          </Grid>
          <Grid item sm={12}>
            <TableContainer component={Paper}>
              <Table aria-label="custom pagination table">
                <TableHead>
                  <TableRow>
                    <TableCell>Dalyko pavadinimas</TableCell>
                    <TableCell className="activity" />
                  </TableRow>
                </TableHead>
                <TableBody>
                  {subjects.map((subject) => (
                    <TableRow key={subject.id}>
                      <TableCell component="th" scope="row">
                        {subject.name}
                      </TableCell>
                      <TableCell align="center" className="activity">
                        <Link to={"/subjects/edit/" + subject.id}>
                          <Button variant="contained">Redaguoti</Button>
                        </Link>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Grid>
          <Grid item sm={12} marginTop={2} marginBottom={10}>
            <Stack direction="row" spacing={2}>
              <Link to={"/modules/edit/" + module.id}>
                <Button id="edit-button-view-module " variant="contained">
                  Redaguoti
                </Button>
              </Link>
              <Link to="/modules">
                <Button id="back-button-view-module " variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
