import {
  Button,
  Checkbox,
  FormControlLabel,
  FormGroup,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function ModuleList() {
  const [modules, setModules] = useState([]);
  const [filteredModules, setFilteredModules] = useState([]);
  const [deletedModules, setDeletedModules] = useState([]);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [isChecked, setChecked] = useState(false);

  // useEffect(() => {
  //   fetch("api/v1/modules")
  //     .then((response) => response.json())
  //     .then((data) => {
  //       setModules(data);
  //       return data;
  //     })
  //     .then((data) => setFilteredModules(data));
  // }, []);

  // useEffect(() => {
  //   fetch("api/v1/modules/deleted")
  //     .then((response) => response.json())
  //     .then(setDeletedModules);
  // }, []);

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - modules.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleSearch = (event) => {
    if (event.length === 0) {
      setFilteredModules(modules);
    } else {
      const filtered = modules.filter((module) => {
        const moduleName = module.name.toLowerCase();
        return (
          moduleName.includes(event.toLowerCase())
        );
      });
      setFilteredModules(filtered);
    }
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item lg={10}>
            <h3>Modulių sąrašas</h3>
          </Grid>
          <Grid item lg={2}>
            <Link to="/modules/create">
              <Button variant="contained">Pridėti naują</Button>
            </Link>
          </Grid>

          <Grid item lg={12}>
            <TextField
              fullWidth
              variant="outlined"
              name="search-form"
              label="Paieška"
              id="search-form"
              onChange={(e) => handleSearch(e.target.value)}
            ></TextField>
          </Grid>
        </Grid>
        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell>Modulio kodas</TableCell>
                <TableCell>Modulio pavadinimas</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0
                ? filteredModules.slice(
                    page * rowsPerPage,
                    page * rowsPerPage + rowsPerPage
                  )
                : filteredModules
              ).map((module) => (
                <TableRow key={module.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/modules/view/" + module.id}>
                      {module.number}
                    </Link>
                  </TableCell>
                  <TableCell>{module.name}</TableCell>
                </TableRow>
              ))}

              {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                  colSpan={3}
                  count={filteredModules.length}
                  page={page}
                  SelectProps={{
                    inputProps: {
                      "aria-label": "Rodyti po",
                    },
                    native: true,
                  }}
                  onPageChange={handleChangePage}
                  rowsPerPage={rowsPerPage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                ></TablePagination>
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>

        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Ištrinti dalykai"
            onChange={(e) =>
              e.target.checked ? setChecked(true) : setChecked(false)
            }
          />
        </FormGroup>

        {isChecked && (
          <TableContainer component={Paper}>
            <Table aria-label="custom pagination table">
              <TableHead>
                <TableRow>
                  <TableCell>Dalyko pavadinimas</TableCell>
                  <TableCell>Modulio pavadinimas</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? deletedModules.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : deletedModules
                ).map((module) => (
                  <TableRow key={module.id}>
                    <TableCell component="th" scope="row">
                      <Link to={"/modules/view/" + module.id}>
                        {module.number}
                      </Link>
                    </TableCell>
                    <TableCell>{module.name}</TableCell>
                  </TableRow>
                ))}

                {emptyRows > 0 && (
                  <TableRow style={{ height: 53 * emptyRows }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    colSpan={3}
                    count={deletedModules.length}
                    page={page}
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePage}
                    rowsPerPage={rowsPerPage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                  ></TablePagination>
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        )}
      </Container>
    </div>
  );
}
