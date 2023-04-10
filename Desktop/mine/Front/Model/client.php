<?php
class Client
{
    private ?string $name = null;
    private ?string $email = null;
    private ?string $pass= null;
    private ?string $repass = null;

    public function __construct($n, $e, $p, $r)
    {
        $this->name = $n;
        $this->email = $e;
        $this->pass = $p;
        $this->repass = $r;
    }

    public function getname()
    {
        return $this->name;
    }

    public function getemail()
    {
        return $this->email;
    }
    
    public function getpass()
    {
        return $this->pass;
    }

    public function getrepass()
    {
        return $this->repass;
    }


    public function setname($n)
    {
        return $this->name= $n;

    }
    
    public function setemaiil($e)
    {
        return $this->email= $e;

    }

    public function setpass($p)
    {
        return $this->pass= $p;

    }

    public function setrepass($r)
    {
        return $this->name= $r;

    }

}
?>