for i = 1,#KEYS do
    local quantity = redis.call('GET', KEYS[i])
    if not quantity then return 'NOTEXIST'
    else redis.call('INCRBY', KEYS[i], ARGV[i]) end
end
return "OK"