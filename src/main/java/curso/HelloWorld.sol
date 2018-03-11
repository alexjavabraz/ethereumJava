pragma solidity ^0.4.19;

contract HelloWorld {
    string nome;
    string public sobreNome;
    address owner;
        
    function HelloWorld() { owner = msg.sender; }
        
    function kill() public { if (msg.sender == owner) selfdestruct(owner); }
    
    function setNome(string _nome) public {
        nome = _nome;
    }

    function getNome() public constant returns (string) {
        return nome;
    }

    function setSobreNome() public payable returns (uint8){
        require(msg.value == 1 ether);
        return 1;
    }

    function getSobreNome() public constant returns (string sobreNome){}

    function sorteio(uint256 numeroDaSorte) public payable returns (string){
        require(msg.value == 1 ether);

        if(numeroDaSorte == 1){
                uint256 amount = 1 ether;
                msg.sender.transfer(amount);
                return "parabens voce acertou o numero 1";
        }

        return "tente outra vez";
        
    }
    
}