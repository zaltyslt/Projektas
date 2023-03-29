import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  Button,
  Grid,
  Stack,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import { Container } from "@mui/system";
import "./Program.css";

export function ViewProgram() {
  const [program, setProgram] = useState({});
  const params = useParams();
  const [subjects, setSubjects] = useState([])
  let navigate = useNavigate();

  useEffect(() => {
    fetch(`api/v1/programs/program/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setProgram(data);
        setSubjects(data.subjectHoursList)
      });
  }, [params.id]);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={8} marginTop={4}>
            <header>
              <h1>{program.programName}</h1>
              <div>
                <span id="modified-date">Paskutinį kartą redaguota: {program.modifiedDate}</span>
              </div>
            </header>
          </Grid>
          <Grid item sm={8}>
            <h4>Programos aprašas</h4>
            <p>{program.description}</p>
          </Grid>
          <TableContainer component={Paper}>
            <Table aria-label="custom pagination table">
              <TableHead>
                <TableRow>
                  <TableCell>Pavadinimas</TableCell>
                  <TableCell>Valandos</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {subjects
                  .map((program) => (
                    <TableRow key={program.id}>
                      <TableCell component="th" scope="row">
                        {program.subjectName ? (
                          program.deleted ? (
                            <span className="Deleted">
                              {program.subjectName}
                            </span>
                          ) : (
                            program.subjectName
                          )
                        ) : (
                          <span>Nenurodytas</span>
                        )}
                      </TableCell>
                      <TableCell>{program.hours}</TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Grid item sm={8}>
            <Stack direction="row" spacing={2} marginTop={2}>
              <Link to={`/update-program/${program.id}`}>
                <Button id="edit-button-view-program" variant="contained">Redaguoti</Button>
              </Link>
              <Button id="back-button-view-program" variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
