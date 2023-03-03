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
import "./ModuleList.css";
import Stack from "@mui/material/Stack";
import ".././pages.css"

export function ModuleList() {
  const [modules, setModules] = useState([]);
  const [filteredModules, setFilteredModules] = useState([]);
  const [deletedModules, setDeletedModules] = useState([]);
  const [page, setPage] = useState(0);
  const [pageInDeleted, setPageInDeleted] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [rowsPerPageInDeleted, setRowsPerPageInDeleted] = useState(10);
  const [isChecked, setChecked] = useState(false);

  useEffect(() => {
    fetchModules();
  }, []);

  useEffect(() => {
    fetchDeletedModules();
  }, []);

  const fetchModules = async () => {
    await fetch("api/v1/modules")
      .then((response) => response.json())
      .then((data) => {
        setModules(data);
        return data;
      })
      .then((data) => setFilteredModules(data));
  };

  const fetchDeletedModules = async () => {
    await fetch("api/v1/modules/deleted")
      .then((response) => response.json())
      .then(setDeletedModules);
  };

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - modules.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const emptyRowsInDeleted =
  pageInDeleted > 0 ? Math.max(0, (1 + pageInDeleted) * rowsPerPageInDeleted - deletedModules.length) : 0;
  
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangePageInDeleted = (event, newPage) => {
    setPageInDeleted(newPage);
  };

  const handleChangeRowsPerPageInDeleted = (event) => {
    setRowsPerPageInDeleted(parseInt(event.target.value, 10));
    setPageInDeleted(0);
  };

  const handleSearch = (event) => {
    if (event.length === 0) {
      setFilteredModules(modules);
    } else {
      const filtered = modules.filter((module) => {
        const moduleName = module.name.toLowerCase();
        return moduleName.includes(event.toLowerCase());
      });
      setFilteredModules(filtered);
    }
  };

  const handleRestore = async (id) => {
    await fetch("/api/v1/modules/restore/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(fetchModules)
      .then(fetchDeletedModules);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Modulių sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Stack direction="row" justifyContent="flex-end">
              <Link to="/modules/create">
                <Button variant="contained">Pridėti naują</Button>
              </Link>
            </Stack>
          </Grid>

          <Grid item sm={12}>
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
                <TableCell className="empty-activity"></TableCell>
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
                  <TableCell></TableCell>
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
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
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
            label="Ištrinti moduliai"
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
                  <TableCell>Modulio kodas</TableCell>
                  <TableCell>Modulio pavadinimas</TableCell>
                  <TableCell className="activity"></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? deletedModules.slice(
                      pageInDeleted * rowsPerPageInDeleted,
                      pageInDeleted * rowsPerPageInDeleted + rowsPerPageInDeleted
                    )
                  : deletedModules
                ).map((module) => (
                  <TableRow key={module.id}>
                    <TableCell component="th" scope="row">
                      {module.number}
                    </TableCell>
                    <TableCell component="th" scope="row">{module.name}</TableCell>
                    <TableCell align="center" className="activity">
                      <Button 
                        variant="contained"
                        onClick={() => handleRestore(module.id)}
                      >
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}

                {emptyRowsInDeleted > 0 && (
                  <TableRow style={{ height: 53 * emptyRowsInDeleted }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    labelDisplayedRows={({ from, to, count }) =>
                      `${from}-${to} iš ${count}`
                    }
                    colSpan={3}
                    count={deletedModules.length}
                    page={pageInDeleted}
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePageInDeleted}
                    rowsPerPage={rowsPerPageInDeleted}
                    onRowsPerPageChange={handleChangeRowsPerPageInDeleted}
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
