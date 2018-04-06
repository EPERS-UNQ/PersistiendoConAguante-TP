var EspecieFullBox = React.createClass({
  getInitialState: function() {
    return {data: []};
  },
  componentDidMount: function() {
    var url = "/api/raza/" + this.props.raza;
    $.ajax({
      url: url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(url, status, err.toString());
      }.bind(this)
    });
  },

  createPersonaje: function() {
    var nombre = this.state.nombrePersonaje;
      var clase = this.state.clase;
    var url = "/api/raza/" + this.props.raza + "/" + nombre+ "/" + clase;
    $.ajax({
      url: url,
      dataType: 'json',
      cache: false,
      method: "POST",
      success: function(data) {
        this.setState({data: data.raza});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(url, status, err.toString());
      }.bind(this)
    });
  },

  handleNombreChange: function(e) {
     this.setState({nombrePersonaje: e.target.value});
  },

    handleClaseChange: function(e) {
        this.setState({clase: e.target.value});
    },

  render: function() {
    return (
      <div className="especieFullBox">
        <div className="col col-sm-6">
          <img src={this.state.data.urlFoto} />
        </div>
        <div className="col col-sm-6">

          <form className="especieForm" >
              <span className="especieNombre">{this.state.data.nombre} </span>
            <dl>
              <dt>Nombre</dt>
              <dd><input type="text" placeholder="Nombre" value={this.state.data.nombre} disabled={true}/></dd>
              <dt>Altura</dt>
              <dd><input type="number" placeholder="Altura" value={this.state.data.altura} disabled={true}/></dd>
              <dt>Peso</dt>
              <dd><input type="number" placeholder="Peso" value={this.state.data.peso} disabled={true}/></dd>
              <dt>Energia inicial</dt>
              <dd><input type="number" placeholder="Energia inicial" value={this.state.data.energiaInicial}  disabled={true}/></dd>
              <dt>Clases</dt>
              <dd>
                <ReactBootstrap.FormControl componentClass="select" multiple disabled={true} >
                    {this.state.data.clases && this.state.data.clases.map( clase => <option value={clase}>{clase}</option>)}
                </ReactBootstrap.FormControl>
              </dd>
              <dt>Cantidad Personajes</dt><dd>{this.state.data.cantidadPersonajes}</dd>
            </dl>
          </form>
        </div>

        <div className="col col-12 personajeForm" >
          <ReactBootstrap.Button onClick={this.createPersonaje} className="crearBicho col-3" disabled={!this.state.clase || !this.state.nombrePersonaje}>
              Crear personaje
          </ReactBootstrap.Button>
          <input type="text" placeholder="Nombre del personaje" className="col-4" onChange={this.handleNombreChange} />
          <select placeholder="Tipo" onChange={this.handleClaseChange} className="col-4">
              <option value=""></option>
              {this.state.data.clases && this.state.data.clases.map( clase => <option value={clase}>{clase}</option>)}
          </select>
        </div>
      </div>
    );
  }
});

var EspecieBox = React.createClass({
  getInitialState: function() {
     return { showModal: false };
  },
  close: function() {
     this.setState({ showModal: false });
  },
  open: function() {
     this.setState({ showModal: true });
  },

  render: function() {
    return (
      <div className="especieBox col col-5 col-xs-4 col-sm-3 col-md-3 col-lg-2 col-xl-2">
        <div  onClick={this.open}>
          <img src={this.props.data.urlFoto} />
          {this.props.data.nombre}
        </div>
        <ReactBootstrap.Modal show={this.state.showModal} onHide={this.close} dialogClassName="especieFullBoxModal" bsSize="large">
          <EspecieFullBox raza={this.props.data.id} />
        </ReactBootstrap.Modal>
      </div>
    );
  }
});

var EspecieForm = React.createClass({
  getInitialState: function() {
     return { nombre: '', clases:[], peso: null, altura: null, urlFoto: '/image/empty.png', energia: null};
  },

  handlePesoChange: function(e) {
    this.setState({peso: parseInt(e.target.value)});
  },
  handleAlturaChange: function(e) {
    this.setState({altura: parseInt(e.target.value)});
  },
  handleNombreChange: function(e) {
    this.setState({nombre: e.target.value});
  },
  handleFotoChange: function(e) {
    this.setState({urlFoto: e.target.value});
  },
  handleEnergiaChange: function(e) {
    this.setState({energia: parseInt(e.target.value)});
  },
  handleClaseChange: function(e) {
    this.setState({clases: this.getValueOfMultipleSelectBox()});
  },

   getValueOfMultipleSelectBox: function (){
      var select = ReactDOM.findDOMNode(this.refs.clasesInput)
      var options = select.options;
      var firstIndex = select.selectedIndex;
      var selectedValues = [];
      var currentOption;
      if (firstIndex === -1) return [];
      for (var i = firstIndex; i < options.length; ++i) {
          currentOption = options[i];
          if (currentOption.selected) {
              selectedValues.push(currentOption.value);
          }
      }
      return selectedValues;
  },

  handleSubmit: function(e) {
      e.preventDefault();
      var raza = {
        nombre: this.state.nombre.trim(),
        altura: this.state.altura,
        peso: this.state.peso,
        urlFoto: this.state.urlFoto,
        energiaInicial: this.state.energia,
          clases: this.state.clases
      };
      console.log(JSON.stringify(raza));

      $.ajax({
        url: '/api/raza',
        contentType: 'application/json',
        dataType: 'json',
        cache: false,
        method: "POST",
        data: JSON.stringify(raza),
        success: function(data) {
          console.log(data)
        }.bind(this),
        error: function(xhr, status, err) {
          console.error("/api/raza", status, err.toString());
        }.bind(this)
      });
  },

  render: function() {
    return (
        <div>
          <div className="col col-sm-6">
              <img className="image_form" src={this.state.urlFoto} />
          </div>
          <div className="col col-sm-6">
            <form className="especieForm" onSubmit={this.handleSubmit}>
              <h4>Nueva Raza</h4>
              <dl>
                <dt>Nombre</dt>
                <dd><input type="text" placeholder="Nombre" value={this.state.nombre} onChange={this.handleNombreChange} /></dd>
                <dt>Altura</dt>
                <dd><input type="number" placeholder="Altura" value={this.state.altura} onChange={this.handleAlturaChange} /></dd>
                <dt>Peso</dt>
                <dd><input type="number" placeholder="Peso" value={this.state.peso} onChange={this.handlePesoChange} /></dd>
                <dt>Energia inicial</dt>
                <dd><input type="number" placeholder="Energia inicial" value={this.state.energia} onChange={this.handleEnergiaChange} /></dd>
                <dt>Imagen</dt>
                <dd><input type="url" placeholder="Imagen" value={this.state.urlFoto} onChange={this.handleFotoChange} /></dd>
                <dt>Clase</dt>
                <dd>
                  <ReactBootstrap.FormControl componentClass="select" multiple onChange={this.handleClaseChange.bind(this)} ref="clasesInput">
                    <option value="BRUJO">BRUJO</option>
                    <option value="DRUIDA">DRUIDA</option>
                    <option value="CABALLERO">CABALLERO</option>
                    <option value="SACERDOTE">SACERDOTE</option>
                    <option value="GUERRERO">GUERRERO</option>
                    <option value="MONJE">MONJE</option>
                    <option value="MAGO">MAGO</option>
                    <option value="CAZADOR">CAZADOR</option>
                    <option value="PALADIN">PALADIN</option>
                    <option value="PICARO">PICARO</option>
                  </ReactBootstrap.FormControl>
                </dd>
              </dl>
              <input type="submit" value="Crear raza" className="submitEspecie" />
            </form>
          </div>
        </div>
    );
  }
});

var Bichopedia = React.createClass({
  getInitialState: function() {
    return {data: [], showEspecieForm:false};
  },
  componentDidMount: function() {
    this.loadEspeciesFromServer();
    // setInterval(this.loadEspeciesFromServer, this.props.pollInterval);
  },
  loadEspeciesFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },

  openEspecieForm: function() {
    this.setState({ showEspecieForm: true });
  },
  closeEspecieForm: function() {
     this.setState({ showEspecieForm: false });
  },

  render: function() {
    var boxes = this.state.data.map(function(especie) {
        return (
          <EspecieBox data={especie} key={especie.nombre} />
        );
    });

    return (
      <div className="bichoPedia">
        <div>
          <img id="logo" src="image/logo.png" />
          <ReactBootstrap.Button onClick={this.openEspecieForm} className="mainButton">Nueva raza</ReactBootstrap.Button>
        </div>
        <div className="boxes ">
          <div className="col-sm-12">
            {boxes}
        </div>
        </div>
        <ReactBootstrap.Modal show={this.state.showEspecieForm} onHide={this.closeEspecieForm} bsSize="large">
          <EspecieForm />
        </ReactBootstrap.Modal>
      </div>
    );
  }
});

ReactDOM.render(
  <Bichopedia url="/api/raza" pollInterval={2000}/>,
  document.getElementById('content')
);
