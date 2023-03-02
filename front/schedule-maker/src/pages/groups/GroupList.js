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
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function GroupList() {
  const [groups, setGruoups] = useState([]);
  const [deletedGroups, setDeletedGroups] = useState([]);
  const [filteredGroups, setFilteredGroups] = useState([]);

  const [page, setPage] = useState(0);
  const [pageInDeleted, setPageInDeleted] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [rowsPerPageInDeleted, setRowsPerPageInDeleted] = useState(10);
  const [isChecked, setChecked] = useState(false);

  const fetchDeletedGroups = () => {
    fetch("api/vi/groups/get-inactive")
      .then((response) => response.json())
      .then(setDeletedGroups);
  };

  const fetchGroups = () => {
    fetch("api/v1/group/get-active")
      .then((response) => response.json)
      .then((data) => {
        setGruoups(data);
        setFilteredGroups(data);
      });
  };

  const handleRestore = (id) => {
    fetch("/api/v1/groups/restore/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(fetchGroups)
      .then(fetchDeletedGroups);
  };

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - subjects.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const emptyRowsInDeleted =
    pageInDeleted > 0
      ? Math.max(
          0,
          (1 + pageInDeleted) * rowsPerPageInDeleted - deletedSubjects.length
        )
      : 0;

  const handleChangePageInDeleted = (event, newPage) => {
    setPageInDeleted(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeRowsPerPageInDeleted = (event) => {
    setRowsPerPageInDeleted(parseInt(event.target.value, 10));
    setPageInDeleted(0);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Grupių sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Link to="/groups/create">
              <Stack direction="row" justifyContent="flex-end">
                <Button variant="contained">Pridėti naują</Button>
              </Stack>
            </Link>
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
                <TableCell>Grupė</TableCell>
                <TableCell>Programa</TableCell>
                <TableCell align="center">Mokslo metai</TableCell>
                <TableCell align="center">Studentų kiekis</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              <TableRow>
                <TableCell>JP20</TableCell>
                <TableCell>
                  Illlllllllllllllllllllllllllllgas pavadinimas
                </TableCell>
                <TableCell align="center">2023-2024</TableCell>
                <TableCell align="center">20</TableCell>
              </TableRow>

              {(rowsPerPage > 0
                ? filteredGroups.slice(
                    page * rowsPerPage,
                    page * rowsPerPage + rowsPerPage
                  )
                : filteredGroups
              ).map((group) => (
                <TableRow key={group.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/groups/view/" + group.id}>{group.name}</Link>
                  </TableCell>
                  <TableCell>{group.program}</TableCell>
                  <TableCell align="center">{group.year}</TableCell>
                  <TableCell align="center">{group.studentAmount}</TableCell>
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
                  count={filteredGroups.length}
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
            label="Ištrintos grupės"
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
                  <TableCell>Grupė</TableCell>
                  <TableCell>Programa</TableCell>
                  <TableCell align="center">Mokslo metai</TableCell>
                  <TableCell align="center">Studentų kiekis</TableCell>
                  <TableCell className="activity"></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPageInDeleted > 0
                  ? deletedGroups.slice(
                      pageInDeleted * rowsPerPageInDeleted,
                      pageInDeleted * rowsPerPageInDeleted +
                        rowsPerPageInDeleted
                    )
                  : deletedGroups
                ).map((group) => (
                  <TableRow key={group.id}>
                    <TableCell component="th" scope="row">{group.name}</TableCell>
                    <TableCell>{group.program}</TableCell>
                    <TableCell align="center">{group.year}</TableCell>
                    <TableCell align="center">{group.studentAmount}</TableCell>
                    <TableCell align="center" className="activity">
                      <Button
                        variant="contained"
                        onClick={() => handleRestore(group.id)}
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
                    count={deletedGroups.length}
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
